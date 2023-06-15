import os
import uvicorn
import traceback
import tensorflow as tf

import shutil
from PIL import Image
from urllib.request import Request
import requests
from fastapi import FastAPI, Response, UploadFile
#from utils import load_image_into_numpy_array

from google.oauth2 import service_account
from google.cloud import storage
from config import get_config

config = get_config()
with open("service_account.json", "w") as file:
    file.write(config["json_svc_account"])

credentials = service_account.Credentials.from_service_account_file(
    "./service_account.json"
)


# Initialize Model
# If you already put yout model in the same folder as this main.py
# You can load .h5 model or any model below this line

# If you use h5 type uncomment line below
model = tf.keras.models.load_model('./model_baru.h5')
# If you use saved model type uncomment line below
# model = tf.saved_model.load("./my_model_folder")

app = FastAPI()
api_response = {}
api_responses = []

def uploadtogcs(file):
    gcs = storage.Client(credentials=credentials)
    bucket = gcs.bucket("face-u-bucket")
    blob = bucket.blob(file)
    blob.upload_from_filename(file)
    return blob.public_url

# This endpoint is for a test (or health check) to this server
@app.get("/")
def index():
    return "Hello world from ML endpoint! Success"

# If your model need image input use this endpoint!
@app.post("/predict_image")
def predict_image(uploaded_file: UploadFile, response: Response):
    try:
        # Checking if it's an image
        if uploaded_file.content_type not in ["image/jpeg", "image/png"]:
            response.status_code = 400
            return "File is Not an Image"
        
        savefile = uploaded_file.filename
        with open(savefile, "wb") as buffer:
            shutil.copyfileobj(uploaded_file.file, buffer)

        # In here you will get a numpy array in "image" variable.
        # You can use this file, to load and do processing
        # later down the line
        # image = load_image_into_numpy_array(uploaded_file.file.read())
        image = Image.open(uploaded_file.file)
        print("Image shape:", image.size)
        
        # Step 1: (Optional, but you should have one) Do your image preprocessing
        image = image.resize((224, 224))
        image_array = tf.keras.preprocessing.image.img_to_array(image)
        image_array = tf.expand_dims(image_array, 0)
        image_array = image_array / 255.0
        # Step 2: Prepare your data to your model
        
        # Step 3: Predict the data
        # result = model.predict(...)
        result = model.predict(image_array)
        # Step 4: Change the result your determined API output
        class_labels = ["Acne", "Dark Spot", "Normal", "Redness", "Wrinkles"]
        predicted_class_index = tf.argmax(result, axis=1)[0]
        predicted_class = class_labels[predicted_class_index]

        gcs_url = uploadtogcs(savefile)
        os.remove(savefile)
        
        #api_response["predicted_class"] = predicted_class
        #api_response["image_url"] = gcs_url

        # Store the prediction and image URL
        api_response = {
            "predicted_class": predicted_class,
            "image_url": gcs_url
        }
        api_responses.append(api_response)

        return api_response

    except Exception as e:
        traceback.print_exc()
        response.status_code = 500
        return "Internal Server Error"

#Menampilkan semua prediction 
@app.get("/show_predictions")
def show_prediction():
    if api_responses:
        return {"predictions": api_responses}
    else:
        return {"message": "No predictions available"} 

# Menampilkan prediction terbaru
@app.get("/show_prediction")
def show_prediction():
    if api_responses:
        latest_prediction = api_responses[-1]
        return latest_prediction
    else:
        return {"message": "No predictions available"}

#Menampilkan Image
@app.get("/show_image")
def show_image():
    if api_responses:
        image_url = api_responses[-1].get("image_url")  # Get the last prediction's image URL
        
        if image_url:
            # Mengirimkan header untuk menampilkan gambar
            headers = {"Content-Type": "image/jpeg"}  # Ganti dengan tipe konten yang sesuai
            
            # Mengirimkan permintaan GET ke URL gambar di bucket
            image_response = requests.get(image_url, headers=headers)
            
            # Memastikan respons berhasil
            if image_response.status_code == 200:
                return Response(content=image_response.content, media_type="image/jpeg")
            
        return {"message": "Image not available"}
    else:
        return {"message": "No predictions available"}

#Menampilkan semua image beserta prediction
@app.get("/get_images")
def get_images():
    try:
        gcs = storage.Client(credentials=credentials)
        bucket = gcs.bucket("face-u-bucket")

        images = []
        for blob in bucket.list_blobs():
            image_url = blob.public_url
            image_name = blob.name

            # Retrieve predicted class for the image
            predicted_class = None
            for api_response in api_responses:
                if api_response.get("image_url") == image_url:
                    predicted_class = api_response.get("predicted_class")
                    break

            images.append({
                "image_url": image_url,
                "predicted_class": predicted_class,
                "image_name": image_name
            })

        if images:
            return {"images": images}
        else:
            return {"message": "No images available"}

    except Exception as e:
        traceback.print_exc()
        return {"message": "Internal Server Error"}
        
# Starting the server
# Your can check the API documentation easily using /docs after the server is running
port = os.environ.get("PORT", 8080)

if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=port, timeout_keep_alive=1200)
