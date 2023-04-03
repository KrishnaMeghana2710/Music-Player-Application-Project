function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
  }
  
  // Close the dropdown menu if the user clicks outside of it
  window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
      var dropdowns = document.getElementsByClassName("dropdown-content");
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }
  function uploadFile() {
    // Get the file input element
    const fileInput = document.getElementById('file-upload');
  
    // Create a new FormData object
    const formData = new FormData();
  
    // Add the file to the FormData object
    formData.append('file', fileInput.files[0]);
  
    // Send the file to the server using XMLHttpRequest
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/api/songs/upload');
    console.log(formData);
    xhr.send(formData);
  }