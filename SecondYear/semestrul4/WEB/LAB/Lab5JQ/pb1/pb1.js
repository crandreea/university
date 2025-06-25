function moveItem(fromListId, toListId) {
    let $fromList = $('#' + fromListId);
    let $toList = $('#' + toListId);

    let selectedIndex = $fromList.prop('selectedIndex');
    if (selectedIndex !== -1) {
        let $selectedOption = $fromList.find('option:selected');
        $selectedOption.appendTo($toList);
    }
}


// function moveItem(fromListId, toListId) {
//     let fromList = document.getElementById(fromListId);
//     let toList = document.getElementById(toListId);

//     if (fromList.seletedIndex !== -1) {
//         let selectedOption = fromList.options[fromList.selectedIndex];
//         toList.appendChild(selectedOption);
//     }
// }


