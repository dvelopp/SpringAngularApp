#**SpringAngularApp**
***SpringAngularApp*** is a sandbox web application based on *Spring*, *Angular* and many other technologies.

###**Application structure**

 - Login page
 - Home page
 - Configuration page
 - User management module

Application allows you to navigate accross the pages that you have access to.  *User management* module provides functionallity to create and modify existing users. These users are used to login to the system. 

The application has the following traits:

 - **Single page structure**;
 - **Spring security secures access to resources** *(The server side won't get you data which you don't have access to. While angular will hide every element on the UI which should not be accessable by the user)* ;
 - **Spring MVC based RESTful web services** provides JSON answers on HTTP methods GET/POST/PUT/DELETE;
 - **ORM** based domain model;
 - **Good test coverage** *(Spring mvc tests, unit tests, integration tests)* ;
 - **Server side validation**(Spring validators + angular directives);
 - **Static web pages** *(Only static **HTML**, no JSP and other dynamic pages)* ;
 - **Annotation based Spring and Hibernate configuration**;
 - **Integration tests** with in-memory database H2;
 - **Internalization**

###**Technologies**:

 - **Spring Framework 4** *(Core, Boot, MVC, Transaction, Security, ORM)*;
 - **Hibernate Framework 5**;
 - **Gradle**;
 - **jUnit**;
 - **Java Script** (**AngularJS, Bootstrap**, ajax, *plugins*);
 -  **Bower**

### **NEW Added CRUD for user groups. 
### **NEW branch with gradle tests. 

 ###**Page examples** 

####Login page

 ![enter image description here](https://lh3.googleusercontent.com/WvFIAOKcX25DqV_yfnBzKR0PZCTlPoUV6TObmGPumaMnMS9VlM3RbfCz_QeXNwi-dDDoANVPjHE88BBhhe8YmK7HfogBBJgDNrx07sxxKyJL419jSaD_0OmoUeSKsP8yNabSGiECanEdxIXurES9-wIgJ3ooiDTSpb_gRkfPHIIsMqcaKsNfUtcUfFqeux5e26Pi6EoEGFpZa2jyI3APhHYQCZFP1I7ATYkrHAQK9PY5l3MFBo6DEKsNnT27KcF9iMMrI_A39qaXp041wDO0CqmfcO6PbQJu8hdhxhe9QUsI2A3OWvJM_0vnDIFq3TT7MfdG-kIdoMlBIx8uCNm-dYoB10AhloxvBPRN7hFA_IqHHH6OYhw_iHDxvty5_MD-jqcwBI0SjrjD8gAhHdZLC4gbonajRiUzJKuo2qcxH4ocKchpbgW5oKvPccQNnzNQSLXhmbKJYFn4EBBDxxKcwkkiAVfcpRzRK23xfKQUABOwBepwSh6By3G3hxnWByG_zpKr07lkf6UZxZi6ZMu7bc-KGepsfrSbUB577J87Gbo_LbX6slAvvO2lVum4pZjPRuxp=w1436-h903-no)

####Home page
 
 ![enter image description here](https://lh3.googleusercontent.com/2urbw8n-TYkkDRX_Itf8wMUqnttqVvj7-OssDeYY1u8tWIH6z1zBtCujYyUx9OYpRg8w_5U6h4Y-n0LvIujpQqu5NziGnFUp4royzP677Ik8j1myJZBNUpIpEaj_R1rTNLiEQzSDfFFnV5SjZVFsPF9EBsJGeb6p2AM3vpZf4OB_qmGeI77-NLKt7Hdab6VqXuf6Fkyds6k7KSHBU4qOWTRu5TwW01jME5W1HuVx3NWtCDUtQ7BzqVIAegkvcrFUqkKkZFjFTGbNmoaBv1Kr-qRBR_7bptyoJ2Hi8KTKEyXPOkD3PhmQkInnnuSK7lweafA3ZG9jg7L4KdOlf_yuh_-pqhlUAXxLVAMzi3F8JxQLxdejdRDr2a2DKciBWGDbd4Brl94Hr6aU1S-mo-LI5MYHcdc9oAgNLLvht41BTSab5WJfnAgPZegc9N86xDvgD-HhKyOuBF0S9UsOiHtqnBsvBdEtIPgDbYSUPzll5rS0_ADG6NEc2MJo_Ca_RLtmCW6HZb6hPklT9OpcmkoBi4LMPy1zu-u8kQZfUa5LqRfF-tV-617wEK6W20karPf_1mDa=w1415-h904-no)
 
####User management

![enter image description here](https://lh3.googleusercontent.com/29LT1VM7T9odjuZ0o3zFT77Kt6CQxNxXy4rWLCkDnWsqG4VVOPJTOL2-rN5id_ITNeJ2DeS0e4xu8TrRirku-LJlMqLpmwHLad3-JNK54AZeFgdGrpHaSxxIZDbuSPP4xqznp1dvy-yA1fSmNcdo2-D93wc6WLPXfrypmrle7ADqyxBtowTQI8ZmfZAK6MOaMPyuP34fdmm8mumzC_NCXbFbewuHpbZhvQ0MgQqzYX_Jgzne4Reod58zHnx_v8bUVa_75IQe_W-KebEkBqNZ0Fo-_wHDzjq40ohjjb3Ko02BPgLBEz4jffpP6DVFLfvBMBDMXFFNdlq6aVSk5jvQ1q6c4qH5Ps_yhw_9YIvWaz66orUIxEUFLclN9P3f6u0hBTKcGca1YDwasDIJiXW1-2yeqXnBtDkXuV216OQy8RkcHevsEjTlrJiDtmZT21y6bstEdAZNUSbN1I4RH1lPMS_7O9vLneIaVs3M1DxbjcU9bx_i5QwEFhHVnUAJnQZ9beUvSkAcVGaMqpbn64c1eem1IMog18dvfZvZGvClOlxODYfJaDwGYSLS6Ku-d_oJoNif=w1444-h574-no)

####Validation

![enter image description here](https://lh3.googleusercontent.com/J-BZy7dfHy7xZI8VGQVFjjMrXlU7az5X0MU5T8EVz9ebwh7bGC0VIyjCc9yT45snG6KHne5BLd4hS2ePgzrWQevn-Zozr49aGj5nfvpYaCez5LLnhKQpT8h3tUL7LZ-Fm55078OsypkMxmKdmPGSJZ5uWxq7Lx7mx7Tc4EWV7Qvig6HzUa5woTlxfMsyyMisQbSN_wjhv7eJLbSQASbdRzz0pJTwvuqX-RC7-BvVHASiMwhxfWi9PK8uxM2q4w1cgkJkVquqRHBVRu9_vPtAm6uatXOjRQlevU38hjlJkPUi92Nsj1snYQZwsa_51MeiFc4MC-wOQt-8-Y8_hrFmJIRqE1ywrXLwzXQNGIfyGgKseXhLC_Qr4fo19H-aKfLMtnMSrqshi4khBApcjFedtbv-sa9hdVuTJHwrB0AkOmD-zM5hsvGXK61K7EBR5CIajeEFGjekVotb-u2_tjp6f6VlIKLa7eqvBpUWGgRJOcXO69RBhetKI-lgJSkhGt_4x2XMxWKgD96OBuxT1HbpcnWk5eBnLqyRGAwl3xwng-NWJjbzvF6-8E3j2uy3W7GsQ9TF=w1446-h904-no)

####Internalization
![enter image description here](https://lh3.googleusercontent.com/8Vrbod_Kpr6JJom6oaakVf71dUsyPstA7krxanGwpVpkyl5GWG6-PJs6l273iw4y15-RjjEbwCG0IheqZ-8eDmX-hcAC20BIXPBHvM4DfIwkT2dNdcBL9muu4Pg-jttATmxIK-kGzZOQU6eEuMG1rACdFvY72dipW4PX8wI-RNKpHAUj38BhDyROhWzIbOLQg2flCM56qMrk6kaAKlNeim0Ysmidat8rrc6NbtfkDRC3s3qDxluNzrADv2f7v9WS6T1JOPUVIhl1XxySYalGKcL-nmfmc7Kbn2EVflf2-kXZsgQuesm-d-3CJlyZz0OTY5pwOaYcU8r7TQcrskPJpnGSppc-hx0umkw1FnScoGuDMv3Hqkh1ao3FQb7BasvkmSlrCnmdizKwPEOljoOkpvijZnMN6thPWFOQd-g_L-TIp7k2-MskTnoNAIDOVryG4yworp1iQR0QnACvDtC7iBtnnHAdDFjANbYwAXASsP07UxiijS25t-YaAB2fFmlYgPx7Um8Dp7rdrzuL3cM7Uwg8QNbURMKOM4myfsD_NKq-eIvdRP5-1MUfBN1zd-RRmvvT=w1425-h899-no)

### Quick setup(database) :

In order to setup the database you need to specify its configuration in the following config:
application.properties.
Having configured that, to add the default data, you need to call a REST method that is provided by springAngularApp.system.ws.InitDBController. 
Just call http://localhost:8080/initDB and expect the OK status. 

UPDATED: Screenshots are out of date. 
