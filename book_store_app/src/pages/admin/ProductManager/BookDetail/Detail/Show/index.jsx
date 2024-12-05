import QuillEditor from '@/components/Manager/QuillEditor'
import { formatCurrency } from '@/utils/format'
import { Box, Chip, Grid, Typography } from '@mui/material'


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
  return (
    <Box sx={{
      '> *': {
        marginBottom: 5
      }
    }}>
      <Box>
        <RenderTitle label='Tên sách'></RenderTitle>
        <RenderContent label={info.name} />
      </Box>
      <Grid container sx={{
        ' > *': {
          marginTop: {
            sm: 2,
            lg: 0
          }
        }
      }}>
        <Grid item sm={12} lg={5} >
          <RenderTitle label='Tác giả' />
          <RenderContent label={info.author} />
        </Grid>
        <Grid item sm={12} lg={7} >
          <RenderTitle label='Nhà xuất bản' />
          <RenderContent label={`Nhà xuất bản ${info.publisher}`} />
        </Grid>
      </Grid>
      <Box>
        <RenderTitle label='Tái bản' />
        <RenderContent label={`Lần thứ ${info.reprintEdition}`} />
      </Box>
      <Box>
        <RenderTitle label='Số lượng' />
        <RenderContent label={info.availableQuantity} />
      </Box>
      <Box>
        <RenderTitle label='Kích thước' />
        <RenderContent label={info.size.width} />
        <Typography variant='span'>x</Typography>
        <RenderContent label={info.size.wide} />
        <Typography variant='span'>x</Typography>
        <RenderContent label={info.size.height} />
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
            <RenderContent label={formatCurrency(info.price)} strikethrough />
            <Typography sx={{ position: 'absolute', left: '15px' }}>{formatCurrency(info.price - (info.price * info.discount / 100))}</Typography>
          </Box>
        </Grid>
        <Grid item lg={4} sm={6}>
          <RenderTitle label='Giảm giá' />
          <RenderContent label={`${info.discount}%`} />
        </Grid>
      </Grid>

      <Box>
        <QuillEditor value={info.descripttion} readonly={true} />
      </Box>
    </Box >
  )
}

export default Show
