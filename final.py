#!/usr/bin/env python
# coding: utf-8

# In[1]:


# Ignore  the warnings
import warnings
warnings.filterwarnings('always')
warnings.filterwarnings('ignore')

# data visualisation and manipulation
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import style
import seaborn as sns
 
#model selection
from sklearn.model_selection import train_test_split
from sklearn.model_selection import KFold
from sklearn.metrics import accuracy_score,precision_score,recall_score,confusion_matrix,roc_curve,roc_auc_score
from sklearn.model_selection import GridSearchCV
from sklearn.preprocessing import LabelEncoder

#preprocess.
#from keras.preprocessing.image import ImageDataGenerator

#dl libraraies
from keras import backend as K
from keras.models import Sequential
from keras.layers import Dense
from keras.models import model_from_yaml
from keras.models import load_model
from keras.optimizers import Adam,SGD,Adagrad,Adadelta,RMSprop
from keras.utils import to_categorical

# specifically for cnn
from keras.layers import Dropout, Flatten,Activation
from keras.layers import Conv2D, MaxPooling2D, BatchNormalization
 
import tensorflow as tf

import random as rn

# specifically for manipulating zipped images and getting numpy arrays of pixel values of images.
import cv2                  
import numpy as np  
from tqdm import tqdm
import os                   
from random import shuffle  
from zipfile import ZipFile
from PIL import Image


# In[ ]:





# In[2]:


#model = load_model('https://drive.google.com/open?id=1HIk5UQQvqZiPez1-0CWBvgVAEjBJlX9a')
model = load_model('model.h5')


# In[3]:


#model.summary()


# In[4]:


test = []


# In[5]:


IMG_SIZE = 150


# In[6]:


def make_train_data(DIR):
    for img in tqdm(os.listdir(DIR)):
        #label=assign_label(img,flower_type)
        path = os.path.join(DIR,img)
        img = cv2.imread(path,cv2.IMREAD_COLOR)
        img = cv2.resize(img, (IMG_SIZE,IMG_SIZE))
        
        test.append(np.array(img))
        #Z.append(str(label))


# In[7]:


DIR = 'C:/Complete_setup/uploads/'


# In[8]:


make_train_data(DIR)


# In[9]:


test=np.array(test)

test =test/255


# In[10]:


pred=model.predict(test)
pred_digits=np.argmax(pred,axis=1)


# In[ ]:


import os
import glob

files = glob.glob('C:/Complete_setup/uploads/*')
for f in files:
    os.remove(f)


# In[11]:


def foo():
    #pred_digits
    if(pred_digits[0]==0):
        return('gulab')
    elif(pred_digits[0]==1):
        return('jas')
    elif(pred_digits[0]==2):
        return('lotus')
    elif(pred_digits[0]==3):
        return('mogra')
    elif(pred_digits[0]==4):
        return('zandu')
    else:
        return("Image not identified")
    

# In[ ]:




