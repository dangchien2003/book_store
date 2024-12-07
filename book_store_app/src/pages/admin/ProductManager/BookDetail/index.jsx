import { Box, Grid } from '@mui/material'
// import { useParams } from 'react-router-dom'
import UploadMainImage from './UploadMainImage'
import UploadChildImage from './UploadChilden'
import Detail from './Detail'
import { useEffect, useState } from 'react'
import { getBookDetail } from '@/services/productService/bookService'
import { useParams } from 'react-router-dom'

const BookDetail = () => {
  const { id } = useParams()
  const [info, setInfo] = useState({})
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getBookDetail(id)
      .then(response => {
        setInfo(response.data.result)
        setLoading(false)
      }).catch(() => {
      })
  }, [id])

  return (
    <Grid container >
      <Grid item sm={12} md={3} sx={{ display: 'flex', justifyContent: 'center', padding: '10px' }}>
        <Box sx={{ width: '100%' }}>
          <UploadMainImage image={info.mainImage} id={info.id} />
          <UploadChildImage images={info.childImages} id={info.id} />
        </Box>
      </Grid>
      <Grid item sm={12} md={9} sx={{ paddingRight: '30px' }}>
        {!loading && <Detail info={info} setInfo={setInfo} />}
      </Grid>
    </Grid>
  )
}

export default BookDetail
