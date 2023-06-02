package com.develup.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.develup.dto.FilePath;
import com.develup.dto.ItemDTO;
import com.develup.exception.ItemException;
import com.develup.model.Item;
import com.develup.service.ItemServiceImpl;
import com.opencsv.exceptions.CsvException;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	
	@Autowired
    private ItemServiceImpl itemService;
	
	
	@PostMapping("/load_data_from_csv")
    public ResponseEntity<String> getAllItems(@RequestBody FilePath filePath) throws IOException, CsvException {
    	itemService.readCSVAndSaveToDatabase(filePath.getPathName());
    	return new ResponseEntity<String>("data loaded successfully",HttpStatus.OK);
    }
	
	
	//API 1
    @GetMapping("/top_selling")
    public ResponseEntity<List<Item>> getTopSellingItems(@RequestParam(defaultValue="10") int limit) {
    	List<Item> res=itemService.getTopSellingItems(limit);
    	return new ResponseEntity<List<Item>>(res,HttpStatus.OK);
    }
    
    
    //API 2
    @GetMapping("/product_details/low_stock")
    public long getLowStockItemCount(@RequestParam(defaultValue="100") int threshold) {
        return itemService.getLowStockItemCount(threshold);
    }

    @GetMapping("/product_details/all_items")
    public long getAllItemsCount() {
        return itemService.getAllItemsCount();
    }

    @GetMapping("/product_details/item_groups")
    public ResponseEntity<Map<String, Long>> getItemGroupCounts() {
    	Map<String, Long> res=itemService.getItemGroupCounts();
        return new ResponseEntity<Map<String,Long>>(res,HttpStatus.OK);
    }
    
    
    //API 3
    @PutMapping("/update")
    public ResponseEntity<Item> updateItemDetails(@RequestBody ItemDTO itemDTO) throws ItemException {
    	Item updated=itemService.updateItemDetails(itemDTO);
        return new ResponseEntity<Item>(updated,HttpStatus.OK);
    }

    @DeleteMapping("/update/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        String res=itemService.deleteItem(id);
        return new ResponseEntity<String>(res,HttpStatus.OK);
    }
    
}
