<script setup>
defineProps({
  review: Object
})

defineEmits([
  'edit',
  'delete'
])

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
</script>

<template>
  <div class="review-card">
    <h3>{{ review.title }}</h3>

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

    <p>{{ review.content }}</p>

    <RouterLink :to="`/reviews/${review.id}`">
      <button>
        상세보기
      </button>
    </RouterLink>

    <button @click="$emit('edit', review)">
      수정
    </button>

    <button
        class="delete-button"
        @click="$emit('delete', review.id)"
    >
      삭제
    </button>
  </div>
</template>