package com.ncs.markdownsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.StrikethroughSpan
import android.view.View
import com.ncs.markdownsample.databinding.ActivityMainBinding
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonPlugin
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import io.noties.markwon.image.data.DataUriSchemeHandler
import io.noties.markwon.image.glide.GlideImagesPlugin
import io.noties.markwon.syntax.Prism4jSyntaxHighlight
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.GrammarLocator
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import java.util.concurrent.Executors


@PrismBundle(includeAll = true)

class MainActivity : AppCompatActivity() {

    val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViews()

    }

    private fun setViews() {




        val markwon : Markwon = Markwon.builder(this)
            .usePlugin(ImagesPlugin.create())
            .usePlugin(GlideImagesPlugin.create(this))
            .usePlugin(TablePlugin.create(this))
            .usePlugin(TaskListPlugin.create(this))
            .usePlugin(HtmlPlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configure(registry: MarkwonPlugin.Registry) {
                    registry.require(ImagesPlugin::class.java) { imagesPlugin ->
                        imagesPlugin.addSchemeHandler(DataUriSchemeHandler.create())
                    }
                }
            })
            .build()

        val editor = MarkwonEditor.create(markwon);



//        val defaultText = "## Example of Images\n" +
//                "This is a bridge \n![Image](https://picsum.photos/800)\n" +
//                "\nThis is something else \n![Picsum](https://picsum.photos/700)\n" +
//                "\n This is from cloudnary \n![Cloudnary](https://cloudinary-marketing-res.cloudinary.com/image/upload/f_auto,q_auto/v1667313676/website_2021/Guess_iPhone2)\n" +
//                "\n This is from firebase \n![Firebase](https://firebasestorage.googleapis.com/v0/b/versa-debug.appspot.com/o/apphd.png?alt=media&token=af2095de-6eb2-4d5b-af4f-86257ba2f885)"
//        markwon.setMarkdown(binding.tvMarkdownOutput, defaultText)
//



        binding.etMarkDownInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(input: Editable?) {
                binding.scrollview.post{
                    binding.scrollview.fullScroll(View.FOCUS_DOWN)
                }
                markwon.setMarkdown(binding.tvMarkdownOutput,input.toString())

            }

        })

        binding.etMarkDownInput.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(editor));

        binding.btnClearText.setOnClickListener{
            binding.etMarkDownInput.setText("")
        }

    }
}