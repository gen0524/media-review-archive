<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { RouterLink } from 'vue-router'
import ReviewCard from '../components/ReviewCard.vue'

const reviews = ref([])

const loadWantToWatchReviews = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/reviews')

    reviews.value = response.data.filter(
        review => review.status === 'WANT_TO_WATCH'
    )
  } catch (error) {
    console.error(error)
  }
}

onMounted(() => {
  loadWantToWatchReviews()
})
</script>

<template>
  <div class="container">
    <RouterLink to="/">
      <button>홈으로</button>
    </RouterLink>

    <h1>보고 싶은 목록</h1>

    <p>아직 감상하지 않았지만 보고 싶은 작품들을 모아둔 페이지입니다.</p>

    <div v-if="reviews.length === 0">
      <p>보고 싶은 작품이 없습니다.</p>
    </div>

    <div v-else class="review-list">
      <ReviewCard
          v-for="review in reviews"
          :key="review.id"
          :review="review"
          @edit="() => {}"
          @delete="() => {}"
      />
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 30px;
}

.review-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>