window.addEventListener('load', async () => {

  let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/checkloginstatus', {
      credentials: 'include',
      method: 'GET'
  });

  if (res.status === 200) {
      let userObj = await res.json();

      if (userObj.role === 'Finance Manager') {
          window.location.href = 'finance-manager.html';
      }
  } else if (res.status === 401 || res.status === 404)  {
      window.location.href = '/404/404.html';
  }

});

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async() => {

  let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/logout', {
    method: 'POST', 
    credentials: 'include'
  });
  
  if (res.status === 200){
    window.location.href = '../index.html';
  } else if(res.status === 400 || res.status == 404){
    window.location.href = '../../404/404.html';

  }

});

// submit new reibursement request
let submitNewReibursementButton = document.querySelector('#submit-request-btn');

submitNewReibursementButton.addEventListener('click', async() => {

  let reimbursementTypeInput = document.querySelector('#reimbType');
  let reimbursementAmountInput = document.querySelector('#reimbAmount');
  let reimbursementDescriptionInput = document.querySelector('#reimbDescrip');
  let reimbursementImageInput = document.querySelector('#receipt-file');

  let recieptFile = reimbursementImageInput.files[0];

  let formData = new FormData();
  formData.append('type', reimbursementTypeInput.value);
  formData.append('amount', reimbursementAmountInput.value);
  formData.append('description', reimbursementDescriptionInput.value);
  formData.append('receipt', recieptFile);

  let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/submitRequest', {
    method: 'POST', 
    credentials: 'include',
    body: formData
  });


  let data = await res.text();

  if(res.status === 201 || res.status === 200) {

    let successSubmittedMessage = document.createElement('p');
    let submitDiv = document.querySelector('#submit-info');

    successSubmittedMessage.innerHTML = data;
    successSubmittedMessage.style.color = 'green';
    submitDiv.appendChild(successSubmittedMessage);

    setTimeout(() => window.location.reload(), 1000);

  } else if (res.status === 400 || res.status === 404){
    let submitErrorMessage = document.createElement('p');
    let submitErrorDiv = document.querySelector('#submit-err');

    submitErrorDiv.innerHTML = '';
    submitErrorDiv.innerHTML = data;
    submitErrorMessage.style.color = 'red';

  } else {
    window.location.href = '/404/404.html';
  }

});

// Get the Sidebar
var mySidebar = document.getElementById("#mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("#myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}


