package com.supreeth.firestore.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirestoreInfo{
	
	public FirestoreInfo(Firestore firestore){
		super();
	}
	
	Firestore firestore;
  
	/*An example of adding new map field to Gcp-Firestore for some collections based on some document type*/
	public void updateSomeCollectionForCertainDocumentType() {
		Map<String,String> map = new HashMap<>();
		map.put("name", "supreeth");
		map.put("value","supreeth (SUP)");
		
		ApiFuture<QuerySnapshot> future = firestore.collection("collection-name").whereEqualTo("type", "documentName").get();
		
		List<QueryDocumentSnapshot> documents = null;
		
		try {
			documents = future.get().getDocuments();
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch(ExecutionException e) {
			log.error("couldn't get the documents");
		}
		for(QueryDocumentSnapshot document : documents) {
			if(document.contains("startingMapArrays")) {
				document.getReference().update("startingMapArrays",FieldValue.arrayUnion(map));
				log.info("new map fields added");
			}
		}
	}
}
