package com.develup.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.develup.dto.ItemDTO;
import com.develup.exception.ItemException;
import com.develup.model.Item;
import com.opencsv.exceptions.CsvException;

public interface ItemService {
	
	//load data from csv file
    public void readCSVAndSaveToDatabase(String path) throws IOException, CsvException ;
	
	//API 1
    public List<Item> getTopSellingItems(int limit) ;
    
    //API 2
    public long getLowStockItemCount(int threshold) ;
    public long getAllItemsCount() ;
    public Map<String, Long> getItemGroupCounts() ;
    
    //API 3
    public Item updateItemDetails(ItemDTO itemDTO) throws ItemException ;
    public String deleteItem(Long id) ;

}
