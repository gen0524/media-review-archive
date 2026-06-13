<script setup>
import { ref } from 'vue'
import { useUserStore } from '../stores/userStore'
import axios from 'axios'

const userStore = useUserStore()
const inputName = ref('')
const password = ref('')

const register = async () => {
  try {
    await axios.post(
        'http://localhost:8080/api/users/register',
        {
          username: inputName.value,
          password: password.value
        }
    )

    alert('회원가입 성공')
  } catch (error) {
    alert('회원가입 실패')
    console.error(error)
  }
}

const login = async () => {
  try {
    const response = await axios.post(
        'http://localhost:8080/api/users/login',
        {
          username: inputName.value,
          password: password.value
        }
    )

    userStore.setUser(response.data.username)

    inputName.value = ''
    password.value = ''

  } catch (error) {
    alert('로그인 실패')
    console.error(error)
  }
}

const logout = () => {
  userStore.logout()
}
</script>

<template>
  <div class="login-box">
    <template v-if="userStore.username === 'guest'">
      <input
          v-model="inputName"
          placeholder="사용자 이름"
      />

      <input
          v-model="password"
          type="password"
          placeholder="비밀번호"
      />

      <button @click="register">
        회원가입
      </button>

      <button @click="login">
        로그인
      </button>
    </template>

    <template v-else>
      <p>
        {{ userStore.username }}님 환영합니다.
      </p>

      <button @click="logout">
        로그아웃
      </button>
    </template>
  </div>
</template>

<style scoped>
.login-box {
  background: white;
  padding: 16px;
  margin-bottom: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}
</style>