import { Box } from '@mui/material'
import buttonUpload from '@/assets/image/plus.png'
import { ReactComponent as LoadingIcon } from '@/assets/image/loading1.svg'
import { useEffect, useRef, useState } from 'react'
import { toastError, toastSuccess, toastWarning } from '@/utils/toast'
import { X } from '@mui/icons-material'
import { checkImage } from '@/utils/image'
import { removeImage, uploadImage } from '@/services/productService/bookService'

const UploadChildImage = ({ images, id }) => {
  const [img, setImg] = useState(images || [])
  const [uploading, setUploading] = useState(false)
  const inputRef = useRef(null)

  useEffect(() => {
    setImg(images || [])
  }, [images])

  const handleClickUpload = () => {
    if (img.length >= 5) {
      toastWarning('Tối đa 5 ảnh nhỏ')
      return
    }
    inputRef.current.click()
  }

  const handleChange = (event) => {
    const file = event.target.files[0]

    const message = checkImage(file)
    if (message !== '') {
      toastError(message)
      return
    }

    if (file) {
      setUploading(true)
      callApiUpload(file)
    }
  }

  const callApiUpload = (file) => {
    uploadImage(file, 'child', id).then((response) => {
      setImg(pre => [...pre, response.data.result.url])
    })
      .catch(() => { })
      .finally(() => {
        setUploading(false)
      })
  }

  const handleRemoveImage = (index) => {

    removeImage(id, 'child', index)
      .then(() => {
        setImg((prev) => {
          const updatedImages = [...prev]
          updatedImages.splice(index, 1)
          return updatedImages
        })
        toastSuccess('Xoá thành công')
      })
      .catch(() => { })
  }

  return (
    <Box sx={{ display: 'flex', marginTop: '15px', flexWrap: 'wrap', gap: '5px' }}>
      {img.map((item, index) => {
        return <Box key={index} sx={{ position: 'relative', width: '80px', height: '80px', display: 'flex', justifyContent: 'center', background: 'white', marginTop: '10px' }}>
          <img src={item} style={{ maxWidth: '80px', height: '80px', objectFit: 'contain' }} />
          <X sx={{ position: 'absolute', top: '-10px', right: 0, height: '20px' }} onClick={() => { handleRemoveImage(index) }} />
        </Box>
      })}
      {uploading
        ? <Box sx={{ margin: '10px 0' }}><LoadingIcon width='60px' height='60px' /></Box>
        : <img src={buttonUpload} style={{ width: '60px', height: '60px', margin: '10px 0' }} onClick={handleClickUpload} />}

      <input
        ref={inputRef}
        type="file"
        accept="image/jpeg, image/jpg, image/png"
        hidden
        onChange={handleChange}
      />
    </Box>
  )
}

export default UploadChildImage
