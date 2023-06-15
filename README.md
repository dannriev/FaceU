# README
  I will give you some headstart on createing ML API. 
  Please read every lines and comments carefully. 

# I give you a headstart on text based input and image based input API. 
  To run this server, don't forget to install all the libraries in the
  requirements.txt simply by "pip install -r requirements.txt" 
  and then use "python main.py" to run it

# For ML:
  Please prepare your model either in .h5 or saved model format.
  Put your model in the same folder as this main.py file.
  You will load your model down the line into this code. 
  There are 2 option I give you, either your model image based input 
  or text based input. You need to finish functions "def predict_text" or "def predict_image"

# For CC:
  You can check the endpoint that ML being used, eiter it's /predict_text or 
  /predict_image. For /predict_text you need a JSON {"text": "your text"},
  and for /predict_image you need to send an multipart-form with a "uploaded_file" 
  field. you can see this api documentation when running this server and go into /docs
  I also prepared the Dockerfile so you can easily modify and create a container image
  The default port is 8080, but you can inject PORT environement variable


# © Copyright C23-PS429
  Capstone Project FaceU - Bangkit Academy 2022

# Made with FastAPI
  Deploy on Google Cloud Run
