package net.juude.droidrest.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.juude.droidrest.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by juude on 15-7-24.
 */
public class SocketFragment extends Fragment {
    private static final String TAG = "SocketFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_socket, null);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                normalSocket();
                socketChannel();
                return null;
            }
        });
    }

    private void normalSocket() {
        Socket s = null;
        try {
            s = new Socket("10.1.14.197", 8080);
            Log.d(TAG, "connected : " + s.isConnected());
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            pw.println("Juude is Coming");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void socketChannel () {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("10.1.14.197", 8080));
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put("sfasfsafda".getBytes());
            buf.flip();
            while (buf.hasRemaining()) {
                socketChannel.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
