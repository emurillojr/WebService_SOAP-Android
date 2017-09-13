# WebService_SOAP-Android
WebService SOAP Lab <br>
Make a soap call and return information to the screen. <br>
The webservice you will use is: <br>
http://services.aonaware.com/DictService/DictService.asmx <br>
Make the call to get a definition of a word (you’ll need a word with multiple entries, I used the word “horror”) <br>
You are to ask the user for the word and return the word you asked for  <br>
(from the webservice, not just echoing the EditText widget), and the list of WordDefinitions. <br>
A couple of notes: <br>
• Make sure you add the namespace to the parameter, it doesn’t seem to work if you don’t. <br>
• You need to make the call in a AsyncTask. <br>
• Put the word definitions in a Textview that is inside a ScrollView widget. <br>
