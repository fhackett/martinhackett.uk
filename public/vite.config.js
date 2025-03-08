import tailwindcss from '@tailwindcss/vite'

export default {
  plugins: [
    tailwindcss(),
  ],
  build: {
    rollupOptions: {
      input: {
        index: 'index.html',
        404: '404.html',
        live: 'live.html',
        sound: 'sound.html',
        image: 'image.html',
        text: 'text.html',
      },
    },
  },
}
