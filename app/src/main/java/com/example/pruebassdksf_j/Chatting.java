package com.example.pruebassdksf_j;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pruebassdksf_j.models.ChatMessageWrapper;
import com.example.pruebassdksf_j.models.MainStatusSaver;
import com.example.pruebassdksf_j.models.MessageWrapper;
import com.salesforce.android.chat.core.AgentListener;
import com.salesforce.android.chat.core.ChatBotListener;
import com.salesforce.android.chat.core.ChatClient;
import com.salesforce.android.chat.core.ChatCore;
import com.salesforce.android.chat.core.FileTransferAssistant;
import com.salesforce.android.chat.core.FileTransferRequestListener;
import com.salesforce.android.chat.core.QueueListener;
import com.salesforce.android.chat.core.SessionStateListener;
import com.salesforce.android.chat.core.model.AgentInformation;
import com.salesforce.android.chat.core.model.ChatEndReason;
import com.salesforce.android.chat.core.model.ChatFooterMenu;
import com.salesforce.android.chat.core.model.ChatMessage;
import com.salesforce.android.chat.core.model.ChatSessionState;
import com.salesforce.android.chat.core.model.ChatWindowButtonMenu;
import com.salesforce.android.chat.core.model.ChatWindowMenu;
import com.salesforce.android.chat.core.model.FileTransferStatus;
import com.salesforce.android.service.common.utilities.control.Async;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class Chatting<AddressListActivity> extends AppCompatActivity {
    private static final String CHANNEL_ID = "test";
    public MainStatusSaver instance = MainStatusSaver.getInstance();
    public ChatCore core = instance.chatCore;
    public ChatClient chatClient = instance.chatClient;
    public MessagesListAdapter<MessageWrapper> adapter = instance.adapter;
    public Boolean close = true;
    public ArrayList<byte[]> bbytes = new ArrayList<byte[]>();
    public FileTransferAssistant fTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // UI CUSTOM
        createNotificationChannel();
        close = true;
        if(chatClient == null) {
            setContentView(R.layout.loading_screen);
            core.createClient(this)
                    .onResult((operation, mChatClient) -> chatClient = mChatClient
                            .addSessionStateListener(myStateListener)
                            .addAgentListener(myAgentListener)
                            .addQueueListener(myQueueListener)
                            .addFileTransferRequestListener(myFileTransforeListener)
                            .addChatBotListener(myEinsteinBotListener));
        } else {
            startChater();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        instance.setChatCore(core);
        instance.setAdapter(adapter);
        instance.setChatClient(chatClient);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close = false;
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(close) {
            instance.setChatClient(null);
            instance.setAdapter(null);
        }

    }

    public void startChater() {
        // UI CUSTOM
        setContentView(R.layout.messager);

        MessageInput inputView = findViewById(R.id.input);
        MessagesList messagesList = findViewById(R.id.messagesList);

        if(adapter == null) {
            ImageLoader iL = (imageView, url, payload) -> {
                Picasso.with(this).load(url).into(imageView);
            };
            String senderId = "xd";
            adapter = new MessagesListAdapter<>(senderId, iL);
        }

        messagesList.setAdapter(adapter);

        inputView.setTypingListener(new MessageInput.TypingListener() {
            @Override
            public void onStartTyping() {
                chatClient.setIsUserTyping(true);
            }

            @Override
            public void onStopTyping() {
                chatClient.setIsUserTyping(false);
            }
        });
        inputView.setInputListener( input -> {
            chatClient.sendChatMessage(input.toString());
            adapter.addToStart(new MessageWrapper(new ChatMessageWrapper("xd", "Visitor", input.toString(), Calendar.getInstance().getTime())), true);
            return true;
        });

        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                Intent it = new Intent(Intent.ACTION_PICK);
                it.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(it, 1);

            }
        });

        Button btnClose = findViewById(R.id.closeChat);
        btnClose.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("¿Seguro que querés abandonar el chat?")
                    .setPositiveButton("Sí",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            chatClient.endChatSession();
                            endChater();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
            return;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("¿Archivo correcto?")
                        .setPositiveButton("Sí",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                try {

                                    File f = new File(getPath(data.getData()));

                                    FileInputStream fis = new FileInputStream(f);
                                    Bitmap bm = BitmapFactory.decodeStream(fis);
                                    ByteArrayOutputStream baos =  new ByteArrayOutputStream();
                                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    bbytes.add(baos.toByteArray());
                                    adapter.addToStart(
                                            new MessageWrapper(
                                                    new ChatMessageWrapper("xd", "Visitor", f.getName(), Calendar.getInstance().getTime())
                                            ), true
                                    );
                                    if(fTransfer != null) {
                                        fTransfer.uploadFile(bbytes.get(0), "image/jpeg");
                                        bbytes.remove(0);
                                        fTransfer = null;
                                    } else {
                                        chatClient.sendChatMessage("[Internal Message]: El cliente solicita adjuntar.");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        }
    }

    private String getPath(Uri uri) {
        String[]  data = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void endChater () {
        finish();
    }

    SessionStateListener myStateListener = new SessionStateListener() {
        @Override
        public void onSessionStateChange(ChatSessionState chatSessionState) {
        }

        @Override
        public void onSessionEnded(ChatEndReason chatEndReason) {

            endChater();
        }
    };
    AgentListener myAgentListener = new AgentListener() {
        @Override
        public void onAgentJoined(AgentInformation agentInformation) {
            startChater();
        }

        @Override
        public void onChatTransferred(AgentInformation agentInformation) {

        }

        @Override
        public void onChatMessageReceived(ChatMessage chatMessage) {
            showNotification(chatMessage);
            adapter.addToStart(new MessageWrapper(
                    new ChatMessageWrapper(chatMessage.getAgentId(), chatMessage.getAgentName(), chatMessage.getText(), chatMessage.getTimestamp())
            ), true);
        }

        @Override
        public void onAgentIsTyping(boolean b) {
            TextView tx = findViewById(R.id.writtingTextbox);
            if(b) {
                tx.setText("El agente esta escribiendo...");
            } else {
                tx.setText("");
            }
        }

        @Override
        public void onTransferToButtonInitiated() {
        }

        @Override
        public void onAgentJoinedConference(String s) {
        }

        @Override
        public void onAgentLeftConference(String s) {
        }
    };
    QueueListener myQueueListener = new QueueListener() {
        @Override
        public void onQueuePositionUpdate(int i) {

        }

        @Override
        public void onQueueEstimatedWaitTimeUpdate(int i, int i1) {
        }
    };
    ChatBotListener myEinsteinBotListener = new ChatBotListener() {
        @Override
        public void onChatMenuReceived(ChatWindowMenu chatWindowMenu) {
        }

        @Override
        public void onChatFooterMenuReceived(ChatFooterMenu chatFooterMenu) {
        }

        @Override
        public void onChatButtonMenuReceived(ChatWindowButtonMenu chatWindowButtonMenu) {
        }
    };
    FileTransferRequestListener myFileTransforeListener = new FileTransferRequestListener() {
        @Override
        public void onFileTransferRequest(FileTransferAssistant fileTransferAssistant) {
            if(!bbytes.isEmpty()) {
                fileTransferAssistant.uploadFile(bbytes.get(0), "image/jpeg");
                bbytes.remove(0);
                fTransfer = null;
            } else {
                fTransfer = fileTransferAssistant;
            }
        }

        @Override
        public void onFileTransferStatusChanged(FileTransferStatus fileTransferStatus) {
            if(fileTransferStatus == FileTransferStatus.Canceled) {
                fTransfer = null;
            }
        }
    };

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    public void showNotification(ChatMessage ct) {
        if(!isAppOnForeground(this)) {
            Intent intent = new Intent(this, Chatting.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(String.format(ct.getText()));
            bigText.setBigContentTitle("Ualá");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "test")
                    .setSmallIcon(R.drawable.salesforce_chat_service_icon)
                    .setContentTitle("Mensaje recibido!")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentText(ct.getText())
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setStyle(bigText)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, builder.build());
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}