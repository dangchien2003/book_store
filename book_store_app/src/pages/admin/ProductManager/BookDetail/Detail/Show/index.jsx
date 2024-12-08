import QuillEditor from '@/components/Manager/QuillEditor'
import { getAuthorById } from '@/services/productService/authorService'
import { getPublisherBtId } from '@/services/productService/publisherService'
import { formatCurrency } from '@/utils/format'
import { Box, Chip, Grid, Typography } from '@mui/material'
import { useEffect, useState } from 'react'


const RenderTitle = ({ label }) => {
  return <Typography variant='span' sx={{ fontSize: '18px' }}>{label}: </Typography>
}

const RenderContent = ({ label, strikethrough }) => {
  return <Typography variant='span'
    sx={{
      margin: '0 10px', fontSize: '20px', borderBottom: '2px solid #bfccd0', padding: '0 5px',
      textDecoration: strikethrough ? 'line-through' : 'none'
    }}>{label}</Typography>
}

const status = {
  ON_SALE: {
    label: 'Mở bán',
    color: 'success'
  },
  OUT_OF_SALE: {
    label: 'Dừng bán',
    color: 'error'
  },
  HIDE: {
    label: 'Ẩn khỏi gian hàng',
    color: 'warning'
  }
}

const GenStatus = ({ id }) => {
  const data = status[id] ? status[id] : { label: id, color: 'warning' }
  return (<Chip label={data.label} color={data.color} sx={{ fontSize: '16px', margin: '10px' }} />)
}

const Show = ({ info }) => {

  const [authorName, setAuthorName] = useState('')
  const [publisherName, setPublisherName] = useState('')

  useEffect(() => {
    if (info.authorId !== 0 && info.authorId !== null) {
      getAuthorById(info.authorId)
        .then(response => {
          setAuthorName(response.data.result.name)
        }).catch(() => {
        })
    }

    if (info.publisherId !== 0 && info.publisherId !== null) {
      getPublisherBtId(info.publisherId)
        .then(response => {
          setPublisherName(response.data.result.name)
        }).catch(() => {
        })
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <Box sx={{
      '& > * > * ': {
        marginBottom: '30px !important'
      }
    }} >
      <Box>
        <Box>
          <RenderTitle label='Tên sách'></RenderTitle>
          <RenderContent label={info.name} />
        </Box>
      </Box>
      <Grid container>
        <Grid item sm={12} lg={5}>
          <RenderTitle label='Tác giả' />
          <RenderContent label={authorName} />
        </Grid>
        <Grid item sm={12} lg={7} >
          <RenderTitle label='Nhà xuất bản' />
          <RenderContent label={publisherName ? `Nhà xuất bản ${publisherName}` : ''} />
        </Grid>
      </Grid>
      <Box>
        <Box>
          <RenderTitle label='Tái bản' />
          <RenderContent label={`Lần thứ ${info.reprintEdition}`} />
        </Box>
      </Box>
      <Box>
        <Box>
          <RenderTitle label='Số lượng' />
          <RenderContent label={info.availableQuantity} />
        </Box>
      </Box>
      <Box>
        <Box>
          <RenderTitle label='Kích thước' />
          <RenderContent label={info.bookSize.width} />
          <Typography variant='span'>x</Typography>
          <RenderContent label={info.bookSize.wide} />
          <Typography variant='span'>x</Typography>
          <RenderContent label={info.bookSize.height} />
        </Box>
      </Box>
      <Grid container>
        <Grid item lg={4} sm={6}>
          <RenderTitle label='Số trang' />
          <RenderContent label={info.pageCount} />
        </Grid>
        <Grid item lg={8} sm={6}>
          <RenderTitle label='Trạng thái' />
          <GenStatus id={info.statusCode} />
        </Grid>
      </Grid>

      <Grid container>
        <Grid item lg={4} sm={6}>
          <RenderTitle label='Giá bán' />
          <Box display='inline-block' sx={{ position: 'relative' }}>
            <RenderContent label={formatCurrency(info.price - (info.price * info.discount / 100))} />
            <Typography sx={{ position: 'absolute', left: '15px', textDecoration: 'line-through' }}>{formatCurrency(info.price)}</Typography>
          </Box>
        </Grid>
        <Grid item lg={4} sm={6} >
          <RenderTitle label='Giảm giá' />
          <RenderContent label={`${info.discount}%`} />
        </Grid>
      </Grid>

      <Box>
        <QuillEditor value={info.description} readonly={true} />
      </Box>
    </Box >
  )
}

export default Show
