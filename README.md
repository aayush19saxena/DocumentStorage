# DocumentStorage
A simple REST api written in Java

# Testing the service
curl localhost:8080/DocumentStorage/storage/documents

// For a invalid document id
curl localhost:8080/DocumentStorage/storage/documents/7827

// To create a document with sample input
curl --request POST --data "music4lyf" localhost:8080/DocumentStorage/storage/documents

// To check the document, copy the id returned by the previous command and paste id for {documentID}
curl localhost:8080/DocumentStorage/storage/documents/{documentID}

// To update the document, copy the id and replace it with {documentID}

curl --request PUT --data "musicIsLyf" localhost:8080/DocumentStorage/storage/documents/{documentID}

// To delete the document, replace the {documentID} with the id returned in the second command
curl --request DELETE localhost:8080/DocumentStorage/storage/documents/{documentID}
