
const validTypes = ['image/jpeg', 'image/jpg', 'image/png']
export const checkImage = (file) => {
  if (!file) {
    return
  }

  if (!validTypes.includes(file.type)) {
    return 'Định dạng ảnh không hỗ trợ'
  }

  if (file.size > 1024 * 1024 * 1) {
    return 'Kích thước ảnh quá lớn'
  }

  return ''
}