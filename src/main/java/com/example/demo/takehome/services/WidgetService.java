package com.example.demo.takehome.services;

import com.example.demo.takehome.exceptions.NoSuchWidgetException;
import com.example.demo.takehome.exceptions.WidgetAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.demo.takehome.models.Widget;
import com.example.demo.takehome.respositories.WidgetRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WidgetService {

	private final WidgetRepository widgetRepository;

	@Autowired
	private WidgetService(WidgetRepository widgetRepository) {
		Assert.notNull(widgetRepository, "widgetRepository must not be null");
		this.widgetRepository = widgetRepository;
	}

	public List<Widget> getAllWidgets() {
		return widgetRepository.findAll();
	}

	public Optional<Widget> findByName(String name) {
		return widgetRepository.findById(name);
	}

	public Widget createWidget(Widget widget) {
		Optional<Widget> existingWidget = widgetRepository.findById(widget.getName());
		if (existingWidget.isPresent()) {
			throw new WidgetAlreadyExistsException("Widget Already exists ", "WIDGET_ALREADY_EXIST");
		}
		return widgetRepository.save(widget);
	}

	public Widget updateWidget(String name, Widget updatedWidget) {
		Optional<Widget> existingWidget = widgetRepository.findById(name);
		if (!existingWidget.isPresent()) {
			throw new NoSuchWidgetException("No widget found with name: " + name, "WIDGET_NOT_FOUND");
		}
		existingWidget.get().setDescription(updatedWidget.getDescription());
		existingWidget.get().setPrice(updatedWidget.getPrice());
		return existingWidget.get();
	}

	public void deleteWidget(String name) {
		Optional<Widget> existingWidget = widgetRepository.findById(name);
		if (!existingWidget.isPresent()) {
			throw new NoSuchWidgetException("No widget found with name: " + name, "WIDGET_NOT_FOUND");
		}
		widgetRepository.deleteById(name);
	}

}
