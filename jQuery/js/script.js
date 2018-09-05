$(document).ready(function(){	
	$('#data').jstree({
		"plugins": ["checkbox"]
	});

	$('#data').on("changed.jstree", function(e, data){
		if (data.selected.length){
			$(data.selected).each(function(idx){
				var node = data.instance.get_node(data.selected[idx]);
				console.log('The node is: ' + node.text);
			});
		}
	});
});