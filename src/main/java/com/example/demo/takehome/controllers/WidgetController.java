package com.example.demo.takehome.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.example.demo.takehome.models.Widget;
import com.example.demo.takehome.services.WidgetService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class WidgetController {

	private final WidgetService widgetService;

	public WidgetController(WidgetService widgetService) {
		Assert.notNull(widgetService, "widgetService must not be null");
		this.widgetService = widgetService;
	}

	@GetMapping
	public ResponseEntity<List<Widget>> getAllWidgets() {
		return ResponseEntity.ok(widgetService.getAllWidgets());
	}

	@GetMapping("/{name}")
	public ResponseEntity<Optional<Widget>> getWidgetByName(@PathVariable String name) {
		return ResponseEntity.ok(widgetService.findByName(name));
	}

	@PostMapping
	public ResponseEntity<Widget> createWidget(@RequestBody Widget widget) {
		return ResponseEntity.ok(widgetService.createWidget(widget));
	}

	@PutMapping("/{name}")
	public ResponseEntity<Widget> updateWidget(@PathVariable String name, @RequestBody Widget widget) {
		return ResponseEntity.ok(widgetService.updateWidget(name, widget));
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<Void> deleteWidget(@PathVariable String name) {
		widgetService.deleteWidget(name);
		return ResponseEntity.noContent().build();
	}

}