import os
import uvicorn
import traceback
import tensorflow as tf

import shutil
from PIL import Image
from urllib.request import Request
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
        
        api_response = {
            "predicted_class": predicted_class,
            "image_url": gcs_url
        }

        return api_response

        
    except Exception as e:
        traceback.print_exc()
        response.status_code = 500
        return "Internal Server Error"


# Starting the server
# Your can check the API documentation easily using /docs after the server is running
port = os.environ.get("PORT", 8080)

if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=port, timeout_keep_alive=1200)