package id.ac.stiki.doleno.mhd.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.models.Psychiatrist;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//This is the main prototype of fragmenting

public class PsychiatristProfileContactFragment extends BottomSheetDialogFragment {

    private View view;
    private LayoutInflater inflater;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private Button btnSaveToContact, btnCopyToClipboard, btnShare;
    private Psychiatrist psychiatrist;

    public PsychiatristProfileContactFragment(Psychiatrist psychiatrist) {
        this.psychiatrist = psychiatrist;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_psychiatrist_profile_contact, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        btnSaveToContact = view.findViewById(R.id.btnSaveToContact);
        btnCopyToClipboard = view.findViewById(R.id.btnCopyToClipboard);
        btnShare = view.findViewById(R.id.btnShare);


        //Saving to contact
        btnSaveToContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates a new Intent to insert a contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, psychiatrist.getNumber());
                intent.putExtra(ContactsContract.Intents.Insert.NAME, psychiatrist.getName());

                startActivity(intent);
            }
        });

        btnCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone number", psychiatrist.getNumber());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.showSharingIntent(psychiatrist.getName() + "\n" + psychiatrist.getNumber());
            }
        });
        return view;
    }
}
