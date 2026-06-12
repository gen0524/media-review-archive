<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, RouterLink } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const review = ref(null)
const errorMessage = ref('')

const getCategoryLabel = (category) => {
  const labels = {
    MOVIE: '영화',
    DRAMA: '드라마',
    ANIMATION: '애니',
    TV_SHOW: '예능',
    YOUTUBE: '유튜브',
    BOOK: '책'
  }

  return labels[category] || category
}

const getStatusLabel = (status) => {
  const labels = {
    WANT_TO_WATCH: '보고 싶음',
    WATCHING: '보는 중',
    COMPLETED: '감상 완료'
  }

  return labels[status] || status
}

const loadReview = async () => {
  try {
    const response = await axios.get(
        `http://localhost:8080/api/reviews/${route.params.id}`
    )

    review.value = response.data
  } catch (error) {
    console.error(error)
    errorMessage.value = '리뷰를 찾을 수 없습니다.'
  }
}

onMounted(() => {
  loadReview()
})
</script>

<template>
  <div class="detail-container">
    <RouterLink to="/">
      <button>목록으로</button>
    </RouterLink>

    <div v-if="errorMessage">
      <h2>{{ errorMessage }}</h2>
    </div>

    <div v-else-if="review" class="detail-card">
      <h1>{{ review.title }}</h1>

      <p>
        <strong>카테고리</strong>
        {{ getCategoryLabel(review.category) }}
      </p>

      <p>
        <strong>별점</strong>
        {{ '⭐'.repeat(review.rating) }}
        ({{ review.rating }}/5)
      </p>
      <p>
        <strong>감상 상태</strong>
        {{ getStatusLabel(review.status) }}
      </p>

      <p v-if="review.watchedDate">
        <strong>감상일</strong>
        {{ review.watchedDate }}
      </p>

      <p v-if="review.shortReview">
        <strong>한줄평</strong>
        {{ review.shortReview }}
      </p>

      <p>
        <strong>감상평</strong>
      </p>

      <p>{{ review.content }}</p>

      <p>
        <strong>작성일</strong>
        {{ review.createdAt }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 30px;
}

.detail-card {
  margin-top: 20px;
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}
</style>