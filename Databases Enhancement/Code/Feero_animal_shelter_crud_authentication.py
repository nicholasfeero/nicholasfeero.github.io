from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, username, password):
        # Initializing the MongoClient. This helps to access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the animals collection, and the aac user.
        #
        # You must edit the connection variables below to reflect your own instance of MongoDB!
        #
        # Connection Variables
        #
        HOST = 'localhost'
        PORT = 27017
        DB = 'aac'
        COL = 'animals'
        #
        # Initialize Connection
        #
        # Utilize Local CA and Client Certificate for TLS encryption and authentication
        self.client = MongoClient(
            "mongodb://%s:%s@%s:%d/?authSource=aac&tls=true" %
            (username, password, HOST, PORT),
            tlsCAFile="C:/MongoData/mongo-certs/ca.pem",
            tlsCertificateKeyFile="C:/MongoData/mongo-certs/client.pem"
        )
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        # If there exists of value for data, insert documents into MongoDB database and collection
        if data is not None:
            insert = self.database.animals.insert_one(data)  # data should be dictionary  
            # If data insertion is successful, return True
            # If data insertion fails, return False
            if insert != 0:
                return True
            else:
                return False
        # Else there does not exist a value for data, exception occurs
        else:
            raise Exception("Nothing to save, because data parameter is empty")

# Create method to implement the R in CRUD.
    def read(self, query):
        # If there exists a value for query, return the associated documents from MongoDB database and collection
        if query is not None:
            return self.database.animals.find(query)
        # Else there does not exist a value for query, exception occurs
        else:
            raise Exception("Nothing to read, because query parameter is empty")

# Create method to implement the U in CRUD
    def update(self, searchData, updateData):
        # If there exists a value for searchData, assign variable with set of updated data
        if searchData is not None:
            updated = self.database.animals.update_many(searchData, { "$set" : updateData } )
            # Return updated data count
            return updated.modified_count
        # Else there does not exist a value for searchData, exception occurs
        else:
            raise Exception("Nothing to update, because searchData parameter is empty")
            
# Create method to implement the D in CRUD
    def delete(self, deleteData):
        # If there exists a value for deleteData, assign variable with set of deleted data
        if deleteData is not None:
            result = self.database.animals.delete_many(deleteData)
            # Return deleted data count
            return result.deleted_count
        # Else there does not exist a value for deleteData, exception occurs
        else:
            raise Exception("Nothing to delete, because deleteData parameter is empty")
