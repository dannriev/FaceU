from fastapi import FastAPI, Request, File, UploadFile
from starlette.requests import Request
from fastapi.templating import Jinja2Templates
from pydantic import BaseModel
import numpy as np
import io
import cv2
import pytesseract
import shutil
from PIL import Image


class ImageType(BaseModel):
 url: str

app = FastAPI()
templates = Jinja2Templates(directory="templates")

@app.get("/")
def home():
    return "Hello world! Success"


def read_img(img):
 text = pytesseract.image_to_string(img)
 return(text)

#  , file: bytes = File(...)

@app.post("/extract_text") 
async def extract_text(uploaded_file: UploadFile, request: Request):
    
    label = ""
    if request.method == "POST":
        form = await request.form()
        file = form["uploaded_file"].file
        contents = await form["uploaded_file"].read()
        image_stream = io.BytesIO(contents)
        image_stream.seek(0)
        file_bytes = np.asarray(bytearray(image_stream.read()), dtype=np.uint8)
        frame = cv2.imdecode(file_bytes, cv2.IMREAD_COLOR)
        label =  read_img(frame)
       
        return {"label": label}
   
    return templates.TemplateResponse("index.html", {"request": request, "label": label})