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

    <p>{{ review.content }}</p>

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