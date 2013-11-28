function showSBox($id) {
    var box = document.getElementById($id);
    if (box.style.display == "none") {
        box.style.display = "";
    }
}
function hideSBox($id) {
    var box = document.getElementById($id);
    if (box.style.display == "") {
        box.style.display = "none";
    }
}

