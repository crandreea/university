function moveItem(fromListId, toListId) {
    let fromList = document.getElementById(fromListId);
    let toList = document.getElementById(toListId);

    if (fromList.seletedIndex !== -1) {
        let selectedOption = fromList.options[fromList.selectedIndex];
        toList.appendChild(selectedOption);
    }
}
