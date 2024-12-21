package com.example.demo.takehome.respositories;

import org.springframework.stereotype.Repository;

import com.example.demo.takehome.models.Widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class WidgetRepository {

	private List<Widget> table = new ArrayList<>();


	public List<Widget> deleteById(String name) {
		this.table = table.stream().filter((Widget widget) -> !name.equals(widget.getName()))
				.collect(Collectors.toCollection(ArrayList::new));
		return table;
	}

	public List<Widget> findAll() {
		return table;
	}

	public Optional<Widget> findById(String name) {
		Optional<Widget> result = table.stream().filter((Widget widget) -> name.equals(widget.getName())).findAny();
		return result;
	}

	public Widget save(Widget widget) {
		table.add(widget);
		return widget;
	}

}