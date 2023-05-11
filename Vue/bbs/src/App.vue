<!-- App.vue -->
<template>
  <div class="container">
    <h1> 欢迎来到BBS </h1>
    <LoginForm v-if="showLoginForm" @register="showLoginForm = false" />
    <RegisterForm v-else @login="showLoginForm = true" />
  </div>
</template>

<script>
import LoginForm from './components/LoginForm.vue'
import RegisterForm from './components/RegisterForm.vue'

export default {
  components: {
    LoginForm,
    RegisterForm
  },
  data() {
    return {
      showLoginForm: true
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
}

</style>
