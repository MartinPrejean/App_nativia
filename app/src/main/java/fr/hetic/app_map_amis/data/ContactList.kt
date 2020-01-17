package fr.hetic.app_map_amis.data

class ContactList {

    companion object{

        fun createDataSet(): ArrayList<Contact>{
            val list = ArrayList<Contact>()
            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=1",
                    "Capucine",
                    "capugc@hotmail.fr"

                )
            )
            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=2",
                    "Martin",
                    "martin.prejean@hetic.net"
                )
            )

            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=3",
                    "Louis",
                    "0617945724"
                )
            )
            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=4",
                    "Diego",
                    "0634437187"
                )
            )
            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=5",
                    "Martine",
                    "martine@gmail.fr"
                )
            )
            list.add(
                Contact(
                    "https://i.pravatar.cc/150?img=6",
                    "Quentin",
                    "0748559864"
                )
            )

            return list
        }
    }
}