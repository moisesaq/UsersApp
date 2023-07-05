package moises.com.usersapp.model

import com.google.common.truth.Truth.assertWithMessage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Test

class PictureTest {

    @Test
    fun `picture has invalid url`() {
        val picture = mockk<Picture>()
        every { picture["getSafePicture"]() } returns "any_image"
        every { picture.safePicture() } answers { callOriginal() }
        assertWithMessage("Has invalid picture").that(picture.safePicture()).isNotEmpty()
        verifySequence {
            picture.safePicture()
            picture["getSafePicture"]()
        }
    }
}