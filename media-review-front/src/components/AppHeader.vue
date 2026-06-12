<script setup>
import { useUserStore } from '../stores/userStore'

const userStore = useUserStore()
</script>

<template>
  <header class="header">
    <h1>미디어 리뷰 아카이브</h1>

    <p>
      현재 사용자 :
      {{ userStore.username }}
    </p>

    <p>
      영화, 드라마, 애니, 책을 기록하는 나만의 리뷰 저장소
    </p>
  </header>
</template>

<style scoped>
.header {
  text-align: center;
  margin-bottom: 30px;
}

.header h1 {
  margin-bottom: 8px;
}
</style>