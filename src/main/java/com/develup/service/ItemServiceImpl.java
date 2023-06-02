package com.develup.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.develup.dto.ItemDTO;
import com.develup.exception.ItemException;
import com.develup.model.Item;
import com.develup.repository.ItemRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


@Service
public class ItemServiceImpl implements ItemService{
	
    @Autowired
    private ItemRepository itemRepository;
    
    
    @Override
    public void readCSVAndSaveToDatabase(String path) throws IOException, CsvException {
    	
//    	String path="src/main/resources/static/AssessmentBackend.csv";
    	
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
        	
            List<String[]> rows = reader.readAll();
            
            for (int i = 1; i < rows.size(); i++) {
            	
                String[] row = rows.get(i);
                
                Item item = new Item();
                
                item.setBarcode(row[0]);
			    item.setItemDescription(row[1]);
			    item.setGroupName(row[2]);
			    item.setSize(row[3]);
			    item.setPrice(Double.parseDouble(row[4]));
			    item.setQuantity(Double.parseDouble(row[5]));
			    item.setProfit(Double.parseDouble(row[6]));
			    item.setRemainingQuantity(Double.parseDouble(row[7]));
                
                itemRepository.save(item);
            }
            
        }

    }
    
    @Override
    public List<Item> getTopSellingItems(int limit) {
    	Pageable pageable = PageRequest.of(0, limit, Sort.Direction.DESC, "quantity");
    	Page<Item> itemsPage = itemRepository.findAll(pageable);
    	return itemsPage.getContent();
    }
    
    @Override
    public long getLowStockItemCount(int threshold) {
        return itemRepository.countByRemainingQuantityLessThan(threshold);
    }
    
    @Override
    public long getAllItemsCount() {
        return itemRepository.count();
    }
    
    @Override
    public Map<String, Long> getItemGroupCounts() {
    	List<String> groupNames = itemRepository.findGroupNames();
        return groupNames.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
    
    @Override
    public Item updateItemDetails(ItemDTO itemDTO) throws ItemException {
        Optional<Item> optionalItem = itemRepository.findById(itemDTO.getId());
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setItemDescription(itemDTO.getItemDescription());
            item.setQuantity(itemDTO.getQuantity());
            item.setRemainingQuantity(itemDTO.getRemainingQuantity());
            item.setPrice(itemDTO.getPrice());
            return itemRepository.save(item);
        } else {
        	throw new ItemException("Item not found.");
        }
    }
    
    @Override
    public String deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return "Item deleted successfully";
        } else {
            return "Item not found with id "+id;
        }
    }
    
}
