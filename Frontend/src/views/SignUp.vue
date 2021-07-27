<template>
  <div class="signup">
    <v-card
        class="mx-auto"
        max-width="600"
        flat
        tile>
        <v-container>
        <h1> Sign Up </h1>

        <v-alert type="success" v-if="this.accountCreated === true">
            The account was created!
        </v-alert>
        <v-alert type="error" v-if="this.samePassword === false">
            Passwords are not the same
        </v-alert>
         <v-alert type="error" v-if="this.emailTaken === true">
            The email is already being used
        </v-alert>
         <v-alert type="error" v-if="this.usernameTaken === true">
            The username is already being used
        </v-alert>

        <div id="app">
            <v-app id="inspire">
                <v-form
                ref="form"
                v-model="valid"
                lazy-validation
                >
                <v-text-field
                    v-model="user.username"
                    :counter="20"
                    :rules="nameRules"
                    label="Username"
                    required
                ></v-text-field>

                <v-text-field
                    v-model="user.name"
                    :counter="20"
                    :rules="nameRules"
                    label="Name"
                    required
                ></v-text-field>

                <v-text-field
                    v-model="user.surname"
                    :counter="20"
                    :rules="nameRules"
                    label="Surname"
                    required
                ></v-text-field>
            
                <v-text-field
                    v-model="user.email"
                    :rules="emailRules"
                    label="E-mail"
                    required
                ></v-text-field>

                <v-text-field
                    v-model="password1"
                    :type="false ? 'text' : 'password'"
                    :rules="passwordRules"
                    label="Password"
                    required
                ></v-text-field>

                <v-text-field
                    v-model="password2"
                    :type="false ? 'text' : 'password'"
                    :rules="passwordRules"
                    label="Verify Password"
                    required
                ></v-text-field>
            
                <v-btn
                    color="#f4f1ea"
                    @click="signup"
                >
                    Sign Up
                </v-btn>
                </v-form>
            </v-app>
            </div>
        </v-container>
    </v-card>
  </div>
</template>

<script>

export default {
  name: 'SignUp',
  data: () => ({
    valid: true,
    user: {
        id: '',
        username: '',
        email: '',
        password: '',
        name: '',
        surname: '',
    },
    // username: '',
    // name: '',
    // surname: '',
    password1: '',
    password2: '',
    accountCreated: null,
    samePassword: null,
    usernameTaken: null,
    emailTaken: null,
    nameRules: [
      v => !!v || 'Name is required',
      v => (v && v.length <= 20) || 'Name must be less than 20 characters',
    ],
    email: '',
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
    password: '',
    passwordRules: [
      v => !!v || 'Password is required',
    ],
    select: null,
  }),

  methods: {
    signup () {
        if(this.password1 === this.password2){
            this.samePassword = true;
            this.user.password = this.password1
            console.log(this.user)
            this.$http.post("http://localhost:8080/signup", this.user).then( result => {
            if(result.data !== ''){
                alert(result.data.email)
                alert(result.data.username)
                if(result.data.email !== null) this.emailTaken = true
                if(result.data.username !== null) this.usernameTaken = true
                this.accountCreated = false
            } else {
                this.accountCreated = true
            }})
        } else{
            this.samePassword = false;
        }
    },
  }
}
</script>
