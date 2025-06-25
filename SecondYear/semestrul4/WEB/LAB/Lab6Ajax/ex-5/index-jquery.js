$(document).ready(function () {
    loadDirectory('', $('#tree-view'));
});

function loadDirectory(path, $container) {
    $.ajax({
        url: 'server.php',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ path: path }),
        success: function (data) {
            let ul = $('<ul></ul>');

            if (!Array.isArray(data) || data.length === 0) {
                ul.append($('<li>No content available.</li>'));
            } else {
                data.forEach(function (item) {
                    let li = $('<li></li>').text(item.name);

                    if (item.type === 'directory') {
                        li.addClass('folder');

                        li.on('click', function (e) {
                            e.stopPropagation();

                            if (li.attr('data-loaded')) {
                                const childUl = li.children('ul');
                                if (childUl.length) {
                                    childUl.toggle(); 
                                }
                            } else {
                                loadDirectory(item.path, li);
                                li.attr('data-loaded', 'true');
                            }
                        });

                    } else if (item.type === 'file') {
                        li.addClass('file');

                        li.on('click', function (e) {
                            e.stopPropagation();
                            loadFileContent(item.path);
                        });
                    }

                    ul.append(li);
                });
            }

            $container.append(ul);
        },
        error: function (xhr) {
            let ul = $('<ul></ul>');
            ul.append($('<li>No content available.</li>'));
            $container.append(ul);

            console.error('Server error:', xhr.status);
            alert(xhr.responseText);
        }
    });
}

function loadFileContent(filePath) {
    $.ajax({
        url: 'server.php',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ file: filePath }),
        success: function (response) {
            $('#file-content').text(response);
        },
        error: function (xhr) {
            console.error('Server error while loading file:', xhr.status);
        }
    });
}
