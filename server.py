#!/usr/bin/env python
# coding: utf-8

# In[1]:


from flask import Flask, url_for, send_from_directory, request
import logging, os, sys, time
from werkzeug.utils import secure_filename
import requests


# In[2]:


app = Flask(__name__)


# In[3]:


PROJECT_HOME = 'C:/Complete_setup/'#os.path.dirname(os.path.realpath(__file__))''
UPLOAD_FOLDER = '{}/uploads/'.format(PROJECT_HOME)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


# In[4]:


@app.route('/', methods=['POST'])
def api_root():
        imagefile = request.files['image']
        filename = secure_filename(imagefile.filename)
        print("\n Received image File name : " + imagefile.filename)
        imagefile.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
        
        return requests.get('http://3.22.124.24:80/test').content
                

# In[5]:


@app.route('/test')
def test():
        from final import foo
        return foo()
                

@app.route('/Exit')
def Exit():
        os.execl(sys.executable, os.path.abspath(__file__), *sys.argv)

@app.route('/token')
def token():
        return "cHJvdmlzaW9uAHVzZXIxQGMwY2E3Mi52aWR5by5pbwA2Mzc1NDc4NDQwOAAAMDZiNDAzMjRiMDEyZTZiYTRmN2QxZWQ1ZTRmY2MzNmNlZTZhYmYxY2M1MTEyZDFiNzdiMjRiZTMwMjA0Y2RmNDA4NWE5NjMzYzEyMWNlMWE0NmRhYmM5MWY1MTViYTI5"

# In[ ]:



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=False)


# In[ ]:





# In[ ]:




