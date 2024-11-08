import cryptoJs from 'crypto-js'

async function generateCodeChallenge(codeVerifier) {
  const encoder = new TextEncoder()
  const data = encoder.encode(codeVerifier)

  const wordArray = cryptoJs.lib.WordArray.create(data)
  const hash = cryptoJs.SHA256(wordArray)
  const base64Digest = hash.toString(cryptoJs.enc.Base64)
  return base64Digest
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '')
}

function generateCodeVerifier() {
  const array = new Uint32Array(56)
  window.crypto.getRandomValues(array)
  return array.join('')
}

export { generateCodeChallenge, generateCodeVerifier }