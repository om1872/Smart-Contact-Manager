const registerForm = document.querySelector('.register');
const loginForm = document.querySelector('.login');


async function register(e) {
	e.preventDefault();
	const data = {
		name: this.name.value,
		email: this.email.value,
		password: this.password.value,
		about: this.about.value
	};
	const repeatPassoword = this.repeatPassword.value;
	const agreement = this.agreement.value;
	document.querySelector('.authentication').style.display = 'none';
	document.querySelector('.spinner-grow').classList.remove('hidden');
	const response = await fetch('/register?repeatPassword=' + repeatPassoword + '&agreement=' + agreement, {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	}).then(res => { return res });
	const message = await response.json();


	setTimeout(() => {
		document.querySelector('.authentication').style.display = 'block';
		document.querySelector('.spinner-grow').classList.add('hidden');
	}, 1500);



	if (response.status === 200) {
		document.querySelector('.register-alert').style.display = 'block';
		document.querySelector('.register-alert').classList.add('alert-success');
		document.querySelector('.register-alert').textContent = message[0].content;
		window.location='/';
	} else if (response.status === 416) {
		document.querySelector('.register-alert').style.display = 'block';
		document.querySelector('.register-alert').classList.add('alert-warning');
		document.querySelector('.register-alert').textContent = message[0].content;
	} else if (response.status === 400) {
		message.forEach(msg => {
			document.forms['register'][msg.name].classList.add('is-invalid');
			document.querySelector('#invalid-' + msg.name).innerHTML = msg.content;
		})
	} else {
		document.querySelector('.register-alert').style.display = 'block';
		document.querySelector('.register-alert').classList.add('alert-danger');
		document.querySelector('.register-alert').textContent = message[0].content;
	}
}

async function login(e) {
	e.preventDefault();
	const data = {
		email: this.email.value,
		password: this.password.value,
	};
	const response = await fetch('/login', {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	}).then(res => { return res });
	
	const message= await response.json();
}

function removeValidation() {
	const attr = ['name', 'email', 'password', 'about'];
	attr.forEach(attr => {
		document.forms['register'][attr].classList.remove('is-invalid');
		document.querySelector('#invalid-'+attr).innerHTML = '';
	})
}

registerForm.addEventListener('submit', register);
loginForm.addEventListener('submit', login);

registerForm.addEventListener('reset', removeValidation);