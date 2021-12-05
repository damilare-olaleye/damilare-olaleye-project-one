window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
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
  
    getAndPopulatePastReceipt();

  });


  let logoutBtn = document.querySelector('#logout');

  logoutBtn.addEventListener('click', async() => {

    let res = await fetch('http://localhost:8080/logout', {
      method: 'POST', 
      credentials: 'include'
    });
    
    if (res.status === 200){
      window.location.href = '../../authentication/index.html';
    } else if(res.status === 400 || res.status == 404){
      window.location.href = '../../404/404.html';
  
    }
  
  });

  async function getAndPopulatePastReceipt() {

    let res = await fetch('http://localhost:8080/myPastTickets',{
        credentials: 'include', 
        method: 'GET'

    });

    console.log(res);

  
// image api from postman 
    let receiptData = await res.blob();
    console.log(receiptData);

// get the image url
    const imageURL = URL.createObjectURL(receiptData);
    console.log(imageURL);

    var reader = new FileReader();
    reader.readAsDataURL(receiptData);
    reader.onload = function() {
        var base64data = reader.result; // convert to a string
        console.log(base64data);

        let submitDiv = document.querySelector('#submit-info');
        let imagediv = document.querySelector('#myImage');

        imagediv.innerHTML = base64data;
        submitDiv.appendChild(imagediv);




    }


    

  }



