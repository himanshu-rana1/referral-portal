class Employee {

    constructor(id, name, age, email, designation, role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.designation = designation;
        this.role = role;
    }
}

function jsonToEmployeeObject(obj){
    console.log(obj);
    return new Employee (obj.id, obj.name, obj.age, obj.email, obj.designation, obj.role);
}

function onLogin(){
    const email = document.getElementById('email').value;
    const password = document.getElementById('pwd').value;
    fetch('http://localhost:9090/login', {
    headers: { "Content-Type": "application/json; charset=utf-8" },
    method: 'POST',
    body: JSON.stringify({
        'email': email,
        'password': password
    })
    }).then(response =>{
        if(response.status === 200){
            return response.json();
        } else {
            const error = document.getElementById("error");
            error.innerText = 'Incorrect Username/password';
            throw 'credentials are not correct!';
        }
    }).then(data => {
        console.log(data);
        if(data.jwtToken){
            sessionStorage.setItem('jwtToken', data.jwtToken);
            sessionStorage.setItem('user', JSON.stringify(jsonToEmployeeObject(data.employee)));
            window.location.replace('job-portal.html');
        } else {
            const error = document.getElementById("error");
            error.innerText = 'Credentials are not correct!';
            throw 'credentials are not correct!';
        }
    }).catch((error) => {
        console.log(error)
    });
}

function redirect() {
    if(sessionStorage.getItem('jwtToken')) {
        fetch('http://localhost:9090/getStats', {
            headers: { 
                'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
            },
            method: 'GET'
        }).then(response => {
            if(response.status !== 200){
                window.location.replace('index.html');
                throw("Access Denied!");
            } else {
                return response.json();
            }
        }).then(data => {
            console.log(data);
            const total = document.getElementById('total');
            let count = data.totalReferrals;
            total.innerHTML = '<b>'+count+'</b>';
            const hired = document.getElementById('hired');
            count = data.selectedCandidates;
            hired.innerHTML = '<b>'+count+'</b>';
            const process = document.getElementById('process');
            count = data.inProcess;
            process.innerHTML = '<b>'+count+'</b>'; 
            const reject = document.getElementById('reject');
            count = data.rejectedCandidates;
            reject.innerHTML = '<b>'+count+'</b>'; 
        }).catch((error) => {
            console.log(error);
        });
    } else {
        window.location.replace('index.html');
    }
}

function getAllEmployees(){
    fetch('http://localhost:9090/employee/all', {
        headers: { 
            'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
        },
        method: 'GET'
        }).then(response =>{
            return response.json();
        }).then(data => {
            console.log(sessionStorage.getItem('user'));
            for(let employee of data){
                let emp = document.getElementById('employee');
                let p = document.createElement('p');
                p.innerText = employee.email + ' ' +employee.name;
                emp.appendChild(p);
                console.log(emp);
            }
        }).catch((error) => {
            console.log(error)
        });
}

function populateJobTable(){
    const emp = JSON.parse(sessionStorage.getItem('user'));
    if(emp.role.roleName !== 'ROLE_HR'){
        showOrHide('add-job');
    }
    populateJobTable = noop;
    fetch('http://localhost:9090/hr/all', {
        headers: { 
            'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
        },
        method: 'GET'
        }).then(response =>{
                return response.json();
        }).then(data => {
            console.log(data);
            let table = document.getElementById('job');
            for(let job of data){
                let rowCount = table.rows.length;
                let row = table.insertRow(rowCount);
                //Column 1
                let cell1 = row.insertCell(0);
                cell1.innerHTML = job.jobId;
                //Column 2
                let cell2 = row.insertCell(1);
                cell2.innerHTML = job.title;
                //Column 3
                let cell3 = row.insertCell(2);
                cell3.innerHTML = job.department;
                //Column 4
                let cell4 = row.insertCell(3);
                cell4.innerHTML = job.experienceRequired;
                //Column 5
                let cell5 = row.insertCell(4);
                cell5.innerHTML = job.jobDescription;
                //Column 6
                let cell6 = row.insertCell(5);
                cell6.innerHTML = '<button class="btn apply" title="apply" data-toggle="modal" data-target="#candidateForm" onclick="saveJobId('+job.jobId+')"><i class="material-icons">&#xE254;</i></button>';
                if(emp.role.roleName === 'ROLE_HR') {
                    cell6.innerHTML += '<a class="delete" onclick="deleteJob('+job.jobId+')" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
                }
            }
        }).catch((error) => {
            console.log(error)
        });
}

function noop() {}

function logout(){
    sessionStorage.setItem('jwtToken','');
    sessionStorage.setItem('user', undefined);
    window.location.replace('index.html');
}

function showOrHide(eleId) {
    const x = document.getElementById(eleId);
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }

function submitJobForm(){
    if(!validateJobForm()){
        return false;
    }
    fetch('http://localhost:9090/hr/addJob', {
    headers: { 
        "Content-Type": "application/json; charset=utf-8",
        'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
    },
    method: 'POST',
    body: JSON.stringify({
        'title': document.getElementById('jobTitle').value,
        'department': document.getElementById('dept').value,
        'experienceRequired': document.getElementById('exp').value,
        'jobDescription': document.getElementById('desc').value
    })
    }).then(response =>{
            return response.json();
    }).then(data => {
        const emp = JSON.parse(sessionStorage.getItem('user'));
        if(data) {
            let table = document.getElementById('job');
            let rowCount = table.rows.length;
            let row = table.insertRow(rowCount);
            let cell1 = row.insertCell(0);
            cell1.innerHTML = data.jobId;
            let cell2 = row.insertCell(1);
            cell2.innerHTML = data.title;
            let cell3 = row.insertCell(2);
            cell3.innerHTML = data.department;
            let cell4 = row.insertCell(3);
            cell4.innerHTML = data.experienceRequired;
            let cell5 = row.insertCell(4);
            cell5.innerHTML = data.jobDescription;
            let cell6 = row.insertCell(5);
            cell6.innerHTML = '<button class="btn apply" title="apply" data-toggle="modal" data-target="#candidateForm" onclick="saveJobId('+data.jobId+')"><i class="material-icons">&#xE254;</i></button>';
            if(emp.role.roleName === 'ROLE_HR') {
                cell6.innerHTML += '<a class="delete" onclick="deleteJob('+data.jobId+')" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>';
            }
        }
    }).catch((error) => {
        console.log(error)
    });
}

function validateJobForm() {
    let title = document.forms["job-form"]["jobTitle"].value;
    let dept = document.forms["job-form"]["dept"].value;
    let exp = document.forms["job-form"]["exp"].value;
    let desc = document.forms["job-form"]["desc"].value;
    if (title == null || title == "", dept == null || dept == "", exp == null || exp == "", desc == null || desc == "") {
      alert("Please Fill All Required Field");
      return false;
    }
    return true;
}

function deleteJob(jobId){
    console.log("delete : "+jobId);
    fetch('http://localhost:9090/hr/delete?id='+jobId, {
    headers: { 
        "Content-Type": "application/json; charset=utf-8",
        'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
    },
    method: 'DELETE'
    }).catch((error) => {
        console.log(error);
    });
    let row = event.target.parentNode.parentNode.parentNode;
    row.remove();
}

function saveJobId(jobId){
    sessionStorage.setItem('jobId', jobId);
}

function applyJob(){
    const jobId = sessionStorage.getItem('jobId');
    if(jobId && validateCandidateForm()){
        console.log('apply for : '+ jobId);
        const files = document.getElementById('uploadResume').files;
        const emp = JSON.parse(sessionStorage.getItem('user'));
        let formData = new FormData();
        formData.append('resume', files[0]);
        formData.append('candidateName', document.getElementById('candidateName').value);
        formData.append('relevantExperience', document.getElementById('relevantExp').value);
        formData.append('jobId', jobId);
        formData.append('referredBy', emp.name);
        formData.append('referredByEmployeeId', emp.id);
        formData.append('status','IN-PROCESS');
        for(let val of formData.values()){
            console.log(val);
        }
        fetch('http://localhost:9090/candidate/add', {
            headers: { 
                'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
            },
            method: 'POST',
            body : formData
        }).then(response => {
            return response.json();
        }).then(data => {
            alert('You have successfully applied for '+data.candidateName);
        }).catch((error) => {
            console.log(error);
        });
        sessionStorage.removeItem('jobId');
    }
}

function validateCandidateForm() {
    let name = document.forms["candidate-form"]["candidateName"].value;
    let exp = document.forms["candidate-form"]["relevantExp"].value;
    let resume = document.getElementById('uploadResume').files;
    if (name == "" || exp == "" || !resume) {
      alert("Please Fill All Required Field");
      return false;
    }
    return true;
}

function populateEmployeeTable(){
    const employee = JSON.parse(sessionStorage.getItem('user'));
    if(employee.role.roleName !== 'ROLE_ADMIN'){
        showOrHide('add-employee');
    }
    populateEmployeeTable = noop;
    fetch('http://localhost:9090/employee/all', {
        headers: { 
            'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
        },
        method: 'GET'
        }).then(response =>{
                return response.json();
        }).then(data => {
            console.log(data);
            let table = document.getElementById('employee');
            for(let emp of data){
                let rowCount = table.rows.length;
                let row = table.insertRow(rowCount);
                //Column 1
                let cell1 = row.insertCell(0);
                cell1.innerHTML = emp.id;
                //Column 2
                let cell2 = row.insertCell(1);
                cell2.innerHTML = emp.name;
                //Column 3
                let cell3 = row.insertCell(2);
                cell3.innerHTML = emp.age;
                //Column 4
                let cell4 = row.insertCell(3);
                cell4.innerHTML = emp.email;
                //Column 5
                let cell5 = row.insertCell(4);
                cell5.innerHTML = emp.designation;
                //Column 6
                let cell6 = row.insertCell(5);
                cell6.innerHTML = emp.role.roleName;
                //Column 7
                let cell7 = row.insertCell(6);
                for(let privilege of employee.role.privileges) {
                    if(privilege.groupName === "EMPLOYEE_UPDATE") {
                        cell7.innerHTML = '<button class="btn edit" title="edit" data-toggle="modal" data-target="#candidateForm" onclick="saveEmployee('+emp.id+')"><i class="material-icons">&#xE254;</i></button>';
                    }
                }
                for(let privilege of employee.role.privileges) {
                    if(privilege.groupName === "EMPLOYEE_DELETE") {
                        cell7.innerHTML += '<a class="delete" onclick="deleteEmployee('+emp.id+')" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
                    }
                }
            }
        }).catch((error) => {
            console.log(error);
    });
}

function saveEmployee(empId){
    console.log(empId);
}

function deleteEmployee(empId){
    console.log("delete : "+empId);
    fetch('http://localhost:9090/employee/delete?id='+empId, {
    headers: { 
        "Content-Type": "application/json; charset=utf-8",
        'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
    },
    method: 'DELETE'
    }).catch((error) => {
        console.log(error);
    });
    let row = event.target.parentNode.parentNode.parentNode;
    row.remove();
}

function submitEmployeeForm(){
    const res = validateEmployeeForm();
    if(res){
        console.log(res);
        fetch('http://localhost:9090/employee/create', {
        headers: { 
            "Content-Type": "application/json; charset=utf-8",
            'Authorization': 'Bearer '+ sessionStorage.getItem('jwtToken')
        },
        method: 'POST',
        body: JSON.stringify(res)
        }).then(response =>{
                return response.json();
        }).then(data => {
            const emp = JSON.parse(sessionStorage.getItem('user'));
            if(data) {
                let table = document.getElementById('employee');
                let rowCount = table.rows.length;
                let row = table.insertRow(rowCount);
                let cell1 = row.insertCell(0);
                cell1.innerHTML = data.id;
                let cell2 = row.insertCell(1);
                cell2.innerHTML = data.name;
                let cell3 = row.insertCell(2);
                cell3.innerHTML = data.age;
                let cell4 = row.insertCell(3);
                cell4.innerHTML = data.email;
                let cell5 = row.insertCell(4);
                cell5.innerHTML = data.designation;
                let cell6 = row.insertCell(5);
                cell6.innerHTML = emp.role.roleName;
                let cell7 = row.insertCell(6);
                for(let privilege of emp.role.privileges) {
                    if(privilege.groupName === "EMPLOYEE_UPDATE") {
                        cell7.innerHTML = '<button class="btn edit" title="edit" data-toggle="modal" data-target="#candidateForm" onclick="saveEmployee('+data.id+')"><i class="material-icons">&#xE254;</i></button>';
                    }
                }
                for(let privilege of emp.role.privileges) {
                    if(privilege.groupName === "EMPLOYEE_DELETE") {
                        cell7.innerHTML += '<a class="delete" onclick="deleteEmployee('+data.id+')" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>'
                    }
                }
            }
        }).catch((error) => {
            console.log(error)
        });
    }
}

function validateEmployeeForm(){
    let name = document.forms["employee-form"]["name"].value;
    let age = document.forms["employee-form"]["age"].value;
    let email = document.forms["employee-form"]["email"].value;
    let password = document.forms["employee-form"]["password"].value;
    let role = document.forms["employee-form"]["role"];
    role = role.options[role.selectedIndex].text;
    let designation = document.forms["employee-form"]["designation"].value;
    //****************   READ VALUES FROM THE MULTISELECT DROPDOWN **********/
    // let privileges = [];
    // console.log(role);
    // for (let option of document.getElementById('privileges').options) {
    //     if (option.selected) {
    //         privileges.push({'groupName':option.text});
    //         console.log(option.text);
    //     }
    // }
    
    if (name == "" || age == "" || email == "" || password == "" || role == "" || designation == "") {
      alert("Please Fill All Required Field");
      return null;
    }
    return {
        'name': name,
        'age': age,
        'email': email,
        'password': password,
        'designation': designation,
        'role': role
    };   
}

function populateProfile(){
    const user = JSON.parse(sessionStorage.getItem('user'));
    let ele = document.getElementById('profile-name');
    ele.innerText = user.name;
    ele = document.getElementById('profile-desg');
    ele.innerText = user.designation;
    ele = document.getElementById('profile-email');
    ele.innerText = user.email;
    ele = document.getElementById('profile-role');
    ele.innerText = user.role.roleName;
    ele = document.getElementById('profile-age');
    ele.innerText = user.age;
    ele = document.getElementById('profile-exp');
    ele.innerText = user.ex;
}