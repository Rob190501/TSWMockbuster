function toggleMenuVisibility(contextPath) {
	let menu = document.getElementById("menu");
	let hamburger = document.getElementById("hamburger");
	let visibility = menu.style.visibility;
	
	if(visibility === "visible") {
		menu.style.visibility = "hidden";
		hamburger.src = contextPath + "/images/icons/menu.png"
	}
	else {
		menu.style.visibility = "visible";
		hamburger.src = contextPath + "/images/icons/cross.png"
	}
}