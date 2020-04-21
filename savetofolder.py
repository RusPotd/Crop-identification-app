from flask import Flask, url_for, send_from_directory, request
import os
from PIL import Image
from werkzeug.utils import secure_filename

UPLOAD_FOLDER = 'uploads/'

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route('/', methods=['POST'])

def api_root():
    imagefile = request.files['image']
    filename = secure_filename(imagefile.filename)
    print("\n Received image File name : " + imagefile.filename)
    imagefile.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
    return "Image Uploaded Successfully"



if __name__ == '__main__':
        app.run(host="0.0.0.0", port=80, debug=True)
