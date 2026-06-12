<script setup>
import { ref } from 'vue'
import { useUserStore } from '../stores/userStore'

const userStore = useUserStore()
const inputName = ref('')

const login = () => {
  if (inputName.value.trim() === '') {
    alert('사용자 이름을 입력하세요.')
    return
  }

  userStore.setUser(inputName.value)
  inputName.value = ''
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
          placeholder="사용자 이름 입력"
      />

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