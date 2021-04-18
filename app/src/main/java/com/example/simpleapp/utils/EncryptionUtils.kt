package com.example.simpleapp.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.Key
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PublicKey
import javax.crypto.Cipher

object EncryptionUtils {
    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION) }
    private val keyStore by lazy { KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) } }

    fun decryptData(data: String): String {
        val privateKey = getPrivateKey()
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val encryptedData = Base64.decode(data, Base64.DEFAULT)
        return String(cipher.doFinal(encryptedData))
    }

    fun encryptData(data: String): String {
        val publicKey = getPublicKey()
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun getPrivateKey(): Key? {
        return keyStore.getKey(ALIAS, null)
    }

    private fun getPublicKey(): PublicKey {
        return keyStore.getCertificate(ALIAS).publicKey
    }

    fun createKeysIfNotExists() {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        val alias = keyStore.aliases().toList()
        if (!alias.contains(ALIAS)) {
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                .setDigests(KeyProperties.DIGEST_SHA1)
                .build()
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEY_STORE).apply {
                initialize((keyGenParameterSpec))
                generateKeyPair()
            }
        }
    }

    private const val TRANSFORMATION = "RSA/ECB/OAEPwithSHA-1andMGF1Padding"
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    private const val ALIAS = "com.example.simpleapp.alias"
}