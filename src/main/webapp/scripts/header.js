function toggleMenuVisibility() {
	let menu = document.getElementById("menu");
	let visibility = menu.style.visibility;
	menu.style.visibility = visibility === "visible" ? "hidden" : "visible";
}