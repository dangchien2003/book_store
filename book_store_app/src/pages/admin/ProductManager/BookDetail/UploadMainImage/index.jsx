import { Box } from '@mui/material'
import upload from '@/assets/image/loading1.svg'
import upload1 from '@/assets/image/upload_2.jpg'
import React, { useRef, useState } from 'react'
import { checkImage } from '@/utils/image'
import { toastError } from '@/utils/toast'
import { uploadImage } from '@/services/productService/bookService'

const UploadMainImage = ({ image, id }) => {
  const [imgEdited, setImgEdited] = useState(null)
  const [uploading, setUploading] = useState(false)
  const inputRef = useRef(null)

  const handleClickUpload = () => {
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
    uploadImage(file, 'main', id).then((response) => {
      setImgEdited(response.data.result.url)
    })
      .catch(() => { })
      .finally(() => {
        setUploading(false)
      })
  }

  const genImageSrc = () => {

    if (uploading) {
      return upload
    }

    if (imgEdited) {
      return imgEdited
    }

    if (image) {
      return image
    }


    return upload1
  }

  return (
    <Box sx={{ height: '200px', border: '2px solid #9e3e1f', borderRadius: '10px', width: '200px', display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '5px', background: 'white' }} onClick={handleClickUpload}>
      <img src={genImageSrc()} alt='upload' style={{ height: '100%', maxWidth: '100%', objectFit: 'contain' }} />
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

export default React.memo(UploadMainImage)
