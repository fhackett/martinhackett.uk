import UnoCSS from 'unocss/vite'

export default {
  plugins: [
    UnoCSS(),
  ],
  build: {
    rollupOptions: {
      input: {
        index: 'index.html',
        404: '404.html',
      },
    },
  },
}
