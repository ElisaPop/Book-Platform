<template>
  <div class="login">
    <v-card
        class="mx-auto"
        max-width="600"
        flat
        tile>
        <v-container>

        <h1> Log In </h1>

        <v-alert type="error" v-if="this.loginSuccessful === false">
            Username or password incorrect
        </v-alert>
  
        <div id="app">
            <v-app id="inspire">
                <v-form
                ref="form"
                lazy-validation
                >
                <v-text-field
                    v-model="name"
                    :counter="20"
                    :rules="nameRules"
                    label="Name"
                    required
                ></v-text-field>
            
                <v-text-field
                    v-model="password"
                    :type="false ? 'text' : 'password'"
                    :rules="passwordRules"
                    label="Password"
                    required
                ></v-text-field>
            
                <v-btn
                    color="#f4f1ea"
                    class="mr-4"
                    @click="login"
                >
                    Log In
            
                </v-btn>
            
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
  name: 'LogIn',
  data: () => ({
    id: -1,
    user: [],
    loginSuccessful: null,
    name: '',
    nameRules: [
      v => !!v || 'Username is required',
      v => (v && v.length <= 20) || 'Username must be less than 20 characters',
    ],
    password: '',
    passwordRules: [
      v => !!v || 'Password is required',
    ],
    select: null,
  }),
  methods: {
    login () {
      
        this.$http.get("http://localhost:8080/login?username="+ this.name + "&password=" + this.password).then( result => {
        if(result.data !== ''){
            this.loginSuccessful = true
            this.$store.commit('setUserData', result.data)
            this.user = this.$store.getters.userData
            this.$store.commit('setUserRole', this.user.role)
            this.$router.push({ name: 'Home' })
            console.log("Success: " + this.$store.getters.userData.userId)
        } else {
            this.loginSuccessful = false;
        }})
    },
    signup () {
        this.$router.push({ name: 'SignUp' })
    },
  }
}
</script>
