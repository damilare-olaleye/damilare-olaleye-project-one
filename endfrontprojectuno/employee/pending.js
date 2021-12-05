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
  
    getAndPopulatePendingReimbursement();

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

async function getAndPopulatePendingReimbursement() {
  
  let res = await fetch('http://localhost:8080/myReimbursementStatus',{
    method: 'GET',
    credentials: 'include'
   
});

    let pendingArray = await res.json();
    let tbodyElement = document.querySelector("#pending-table tbody");

    console.log(pendingArray);
    console.log(tbodyElement);

    for (let i = 0; i < pendingArray.length; i++) {

        let myPendingArray = pendingArray[i];

        let tr = document.createElement('tr');

        let tableColOne = document.createElement('td');
        tableColOne.innerHTML = myPendingArray.reimbId;

        let tableColTwo = document.createElement('td');
        tableColTwo.innerHTML = myPendingArray.submitted;

        let tableColThree = document.createElement('td');
        tableColThree.innerHTML = myPendingArray.resolved;

        let tableColFour = document.createElement('td');
        tableColFour.innerHTML = myPendingArray.status;

        let tableColFive = document.createElement('td');
        tableColFive.innerHTML = myPendingArray.type;

        let tableColSix = document.createElement('td');
        tableColSix.innerHTML = myPendingArray.description;

        let tableColSeven = document.createElement('td');
        tableColSeven.innerHTML = myPendingArray.amount;

        let tableColEight = document.createElement('td');
        tableColEight.innerHTML = myPendingArray.author;

        let tableColNine = document.createElement('td');
        tableColNine.innerHTML = myPendingArray.resolver;

        tr.appendChild(tableColOne);
        tr.appendChild(tableColTwo);
        tr.appendChild(tableColThree);
        tr.appendChild(tableColFour);
        tr.appendChild(tableColFive);
        tr.appendChild(tableColSix);
        tr.appendChild(tableColSeven);
        tr.appendChild(tableColEight);
        tr.appendChild(tableColNine);

        tbodyElement.appendChild(tr);

    }


}